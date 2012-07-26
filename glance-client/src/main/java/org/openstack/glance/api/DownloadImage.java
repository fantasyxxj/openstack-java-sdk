package org.openstack.glance.api;

import java.io.InputStream;
import java.util.Calendar;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openstack.glance.GlanceCommand;
import org.openstack.glance.model.Image;
import org.openstack.glance.model.ImageDownload;

public class DownloadImage implements GlanceCommand<ImageDownload> {

	private String id;
	
	public DownloadImage(String id) {
		this.id = id;
	}

	@Override
	public ImageDownload execute(WebTarget target) {
		Response response = target.path("images").path(id).request(MediaType.APPLICATION_OCTET_STREAM).head();
		Image image = new Image();
		image.setUri(response.getHeader("x-image-meta-uri"));
		image.setName(response.getHeader("x-image-meta-name"));
		image.setDiskFormat(response.getHeader("x-image-meta-disk_format"));
		image.setContainerFormat(response.getHeader("x-image-meta-container_format"));
		image.setSize(asInteger(response.getHeader("x-image-meta-size")));
		image.setChecksum(response.getHeader("x-image-meta-checksum"));
		image.setCreatedAt(asCalendar(response.getHeader("x-image-meta-create_at")));
		image.setUpdatedAt(asCalendar(response.getHeader("x-image-meta-updated_at")));
		image.setDeletedAt(asCalendar(response.getHeader("x-image-meta-deleted_at")));
		image.setStatus(response.getHeader("x-image-meta-status"));
		image.setPublic(asBoolean(response.getHeader("x-image-meta-is-public")));
		image.setMinRam(asInteger(response.getHeader("x-image-meta-min-ram")));
		image.setMinDisk(asInteger(response.getHeader("x-image-meta-min-disk")));
		image.setOwner(response.getHeader("x-image-meta-owner"));
		image.setName(response.getHeader("x-image-meta-owner"));
		for(String key : response.getMetadata().keySet()) {
			if(key.startsWith("x-image-meta-property-")) {
				image.getProperties().put(key.substring(22), response.getHeader(key));
			}
		}
		ImageDownload imageDownload = new ImageDownload();
		imageDownload.setImage(image);
		imageDownload.setInputStream((InputStream) response.getEntity());
		return imageDownload;
	}
	
	private Calendar asCalendar(String calendarString) {
		return null;
	}
	
	private Integer asInteger(String integerString) {
		return null;
	}
	
	private Boolean asBoolean(String booleanString) {
		return null;
	}

}
